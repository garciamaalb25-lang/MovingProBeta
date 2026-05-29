package com.salesianostriana.dam.movingprobeta.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.salesianostriana.dam.movingprobeta.model.Mudanza;
import com.salesianostriana.dam.movingprobeta.service.MudanzaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/mudanza")
@RequiredArgsConstructor
public class MudanzaController {

	private final MudanzaService mudanzaService;

	@GetMapping
	public String list(Model model) {
		model.addAttribute("mudanzas", mudanzaService.findAll());
		model.addAttribute("fechaFiltro", null);
		model.addAttribute("fechaInicioFiltro", null);
		model.addAttribute("fechaFinFiltro", null);
		return "mudanza/list";
	}

	@GetMapping("/new")
	public String newForm(Model model) {
		model.addAttribute("mudanza", new Mudanza());
		return "mudanza/form";
	}

	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Long id, Model model) {
		model.addAttribute("mudanza", mudanzaService.findById(id));
		return "mudanza/form";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute Mudanza mudanza, BindingResult result) {
		if (result.hasErrors()) {
			return "mudanza/form";
		}
		mudanzaService.save(mudanza);
		return "redirect:/mudanza";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		mudanzaService.deleteById(id);
		return "redirect:/mudanza";
	}

	@GetMapping("/buscar")
	public String buscarPorFecha(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
			Model model) {
		if (fecha != null) {
			model.addAttribute("mudanzas", mudanzaService.findByFecha(fecha));
			model.addAttribute("fechaFiltro", fecha);
			model.addAttribute("fechaInicioFiltro", null);
			model.addAttribute("fechaFinFiltro", null);
		} else if (fechaInicio != null && fechaFin != null) {
			model.addAttribute("mudanzas", mudanzaService.findByFechaEntreRango(fechaInicio, fechaFin));
			model.addAttribute("fechaFiltro", null);
			model.addAttribute("fechaInicioFiltro", fechaInicio);
			model.addAttribute("fechaFinFiltro", fechaFin);
		} else {
			model.addAttribute("mudanzas", mudanzaService.findAll());
			model.addAttribute("fechaFiltro", null);
			model.addAttribute("fechaInicioFiltro", null);
			model.addAttribute("fechaFinFiltro", null);
		}
		return "mudanza/list";
	}

	@GetMapping("/pdf")
	public void exportarPdf(HttpServletResponse response,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin)
			throws IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=mudanzas.pdf");

		List<Mudanza> mudanzas;
		if (fecha != null) {
			mudanzas = mudanzaService.findByFecha(fecha);
		} else if (fechaInicio != null && fechaFin != null) {
			mudanzas = mudanzaService.findByFechaEntreRango(fechaInicio, fechaFin);
		} else {
			mudanzas = mudanzaService.findAll();
		}

		try {
			Document document = new Document();
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();

			Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
			com.itextpdf.text.Paragraph title = new com.itextpdf.text.Paragraph("MovingPro - Listado de Mudanzas",
					titleFont);
			title.setAlignment(Element.ALIGN_CENTER);
			title.setSpacingAfter(20);
			document.add(title);

			if (fecha != null) {
				com.itextpdf.text.Paragraph filtro = new com.itextpdf.text.Paragraph("Filtro: " + fecha.toString(),
						FontFactory.getFont(FontFactory.HELVETICA, 10));
				filtro.setAlignment(Element.ALIGN_CENTER);
				filtro.setSpacingAfter(10);
				document.add(filtro);
			} else if (fechaInicio != null && fechaFin != null) {
				com.itextpdf.text.Paragraph filtro = new com.itextpdf.text.Paragraph(
						"Filtro: " + fechaInicio + " — " + fechaFin, FontFactory.getFont(FontFactory.HELVETICA, 10));
				filtro.setAlignment(Element.ALIGN_CENTER);
				filtro.setSpacingAfter(10);
				document.add(filtro);
			}

			PdfPTable table = new PdfPTable(6);
			table.setWidthPercentage(100);

			String[] headers = { "Código", "Origen", "Destino", "Coste", "Horas", "Fecha" };
			for (String header : headers) {
				PdfPCell cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new com.itextpdf.text.BaseColor(27, 58, 107));
				cell.setPadding(8);
				com.itextpdf.text.Font whiteFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Font.BOLD,
						com.itextpdf.text.BaseColor.WHITE);
				cell.setPhrase(new Phrase(header, whiteFont));
				table.addCell(cell);
			}

			Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
			for (Mudanza mudanza : mudanzas) {
				table.addCell(new Phrase(String.valueOf(mudanza.getCodigo()), cellFont));
				table.addCell(new Phrase(mudanza.getOrigen(), cellFont));
				table.addCell(new Phrase(mudanza.getDestino(), cellFont));
				table.addCell(new Phrase(mudanza.getCoste() + " €", cellFont));
				table.addCell(new Phrase(String.valueOf(mudanza.getNumeroHoras()), cellFont));
				table.addCell(new Phrase(mudanza.getFecha().toString(), cellFont));
			}

			document.add(table);
			document.close();

		} catch (Exception e) {
			throw new IOException("Error al generar el PDF", e);
		}
	}
}