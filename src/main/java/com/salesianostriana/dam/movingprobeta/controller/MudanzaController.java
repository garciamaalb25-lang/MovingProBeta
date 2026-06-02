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
import com.salesianostriana.dam.movingprobeta.model.EstadoMudanza;
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

// Controlador para manejar las operaciones CRUD de mudanzas, búsqueda por fecha y exportación a PDF
	@GetMapping
	public String list(Model model) {
		List<Mudanza> mudanzas = mudanzaService.findAll();
		model.addAttribute("mudanzas", mudanzas);
		model.addAttribute("fechaFiltro", null);
		model.addAttribute("fechaInicioFiltro", null);
		model.addAttribute("fechaFinFiltro", null);
		return "mudanza/mudanza-list";
	}

// Mostrar formulario para crear nueva mudanza
	@GetMapping("/new")
	public String newForm(Model model) {
		Mudanza mudanza = new Mudanza();
		model.addAttribute("mudanza", mudanza);
		model.addAttribute("isAssigned", false);
		return "mudanza/mudanza-form";
	}

// Mostrar formulario para editar mudanza existente
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Long id, Model model) {
		Mudanza mudanza = mudanzaService.findById(id);
		boolean isAssigned = mudanza.getVehiculos().stream()
				.anyMatch(mv -> mv.getEstado() != EstadoMudanza.TERMINADO);
		model.addAttribute("mudanza", mudanza);
		model.addAttribute("isAssigned", isAssigned);
		return "mudanza/mudanza-form";
	}

// Guardar nueva mudanza o actualizar existente
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute Mudanza mudanza, BindingResult result, Model model) {
		boolean isAssigned = false;
		if (mudanza.getIdMudanza() != null) {
			Mudanza existing = mudanzaService.findById(mudanza.getIdMudanza());
			isAssigned = existing.getVehiculos().stream()
					.anyMatch(mv -> mv.getEstado() != EstadoMudanza.TERMINADO);
			if (isAssigned) {
				if (!existing.getFecha().equals(mudanza.getFecha())) {
					result.rejectValue("fecha", "error.mudanza", "No se puede cambiar la fecha de una mudanza ya asignada a un vehículo.");
				}
				if (existing.getCodigo() != mudanza.getCodigo()) {
					result.rejectValue("codigo", "error.mudanza", "No se puede cambiar el código de una mudanza ya asignada a un vehículo.");
				}
				if (existing.getNumeroHoras() != mudanza.getNumeroHoras()) {
					result.rejectValue("numeroHoras", "error.mudanza", "No se puede cambiar las horas de una mudanza ya asignada a un vehículo.");
				}
				if (!existing.getOrigen().equals(mudanza.getOrigen())) {
					result.rejectValue("origen", "error.mudanza", "No se puede cambiar el origen de una mudanza ya asignada a un vehículo.");
				}
				if (!existing.getDestino().equals(mudanza.getDestino())) {
					result.rejectValue("destino", "error.mudanza", "No se puede cambiar el destino de una mudanza ya asignada a un vehículo.");
				}
			}
		}

		if (result.hasErrors()) {
			model.addAttribute("isAssigned", isAssigned);
			return "mudanza/mudanza-form";
		}
		mudanzaService.save(mudanza);
		return "redirect:/mudanza";
	}

// Eliminar mudanza por id
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		mudanzaService.deleteById(id);
		return "redirect:/mudanza";
	}

// Buscar mudanzas por fecha o rango de fechas
	@GetMapping("/buscar")
	public String buscarPorFecha(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
			Model model) {
		List<Mudanza> mudanzas;
		if (fecha != null) {
			mudanzas = mudanzaService.findByFecha(fecha);
			model.addAttribute("mudanzas", mudanzas);
			model.addAttribute("fechaFiltro", fecha);
			model.addAttribute("fechaInicioFiltro", null);
			model.addAttribute("fechaFinFiltro", null);
		} else if (fechaInicio != null && fechaFin != null) {
			mudanzas = mudanzaService.findByFechaEntreRango(fechaInicio, fechaFin);
			model.addAttribute("mudanzas", mudanzas);
			model.addAttribute("fechaFiltro", null);
			model.addAttribute("fechaInicioFiltro", fechaInicio);
			model.addAttribute("fechaFinFiltro", fechaFin);
		} else {
			mudanzas = mudanzaService.findAll();
			model.addAttribute("mudanzas", mudanzas);
			model.addAttribute("fechaFiltro", null);
			model.addAttribute("fechaInicioFiltro", null);
			model.addAttribute("fechaFinFiltro", null);
		}
		return "mudanza/mudanza-list";
	}

// Exportar listado de mudanzas a PDF con filtros opcionales por fecha
	@GetMapping("/pdf")
	public void exportarPdf(HttpServletResponse response,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin)
			throws IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=mudanzas.pdf");
// Obtener mudanzas según filtros de fecha
		List<Mudanza> mudanzas;
		if (fecha != null) {
			mudanzas = mudanzaService.findByFecha(fecha);
		} else if (fechaInicio != null && fechaFin != null) {
			mudanzas = mudanzaService.findByFechaEntreRango(fechaInicio, fechaFin);
		} else {
			mudanzas = mudanzaService.findAll();
		}
// Generar PDF usando iText
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();

			Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
			Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
			Font whiteFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Font.BOLD,
					com.itextpdf.text.BaseColor.WHITE);

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
// Agregar encabezados de columna con estilo
			String[] headers = { "Código", "Origen", "Destino", "Coste", "Horas", "Fecha" };
			for (String header : headers) {
				PdfPCell cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new com.itextpdf.text.BaseColor(27, 58, 107));
				cell.setPadding(8);
				cell.setPhrase(new Phrase(header, whiteFont));
				table.addCell(cell);
			}

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