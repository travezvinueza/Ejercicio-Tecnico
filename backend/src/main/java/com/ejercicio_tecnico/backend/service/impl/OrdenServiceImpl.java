package com.ejercicio_tecnico.backend.service.impl;

import com.ejercicio_tecnico.backend.dto.ArticuloDto;
import com.ejercicio_tecnico.backend.dto.OrdenDto;
import com.ejercicio_tecnico.backend.entity.Articulo;
import com.ejercicio_tecnico.backend.entity.Cliente;
import com.ejercicio_tecnico.backend.entity.Orden;
import com.ejercicio_tecnico.backend.exception.ArticuloNotFoundException;
import com.ejercicio_tecnico.backend.exception.ClienteNotFoundException;
import com.ejercicio_tecnico.backend.exception.OrdenNotFoundException;
import com.ejercicio_tecnico.backend.repository.ArticuloRepository;
import com.ejercicio_tecnico.backend.repository.ClienteRepository;
import com.ejercicio_tecnico.backend.repository.OrdenRepository;
import com.ejercicio_tecnico.backend.service.OrdenService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenServiceImpl implements OrdenService {

    private final OrdenRepository ordenRepository;
    private final ClienteRepository clienteRepository;
    private final ArticuloRepository articuloRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrdenDto crearOrden(OrdenDto ordenDto, Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + clienteId));

        Orden orden = modelMapper.map(ordenDto, Orden.class);
        orden.setCodigo(generateCodigoUnico());
        orden.setFecha(LocalDate.now());
        orden.setCliente(cliente);

        if (ordenDto.getArticulos() != null && !ordenDto.getArticulos().isEmpty()) {
            List<Articulo> articulos = new ArrayList<>();
            for (ArticuloDto articuloDto : ordenDto.getArticulos()) {
                Articulo articulo = articuloRepository.findById(articuloDto.getId())
                        .orElseThrow(() -> new ArticuloNotFoundException("Artículo no encontrado con ID: " + articuloDto.getId()));

                articulos.add(articulo);
            }
            orden.setArticulos(articulos);
        }

        Orden nuevaOrden = ordenRepository.save(orden);
        return modelMapper.map(nuevaOrden, OrdenDto.class);
    }

    private String generateCodigoUnico() {
        long ordenCount = ordenRepository.count() + 1;
        return String.format("ORDEN-%06d", ordenCount);
    }

    @Override
    public OrdenDto actualizarOrden(Long id, OrdenDto ordenDto) {
        Orden ordenExistente = ordenRepository.findById(id)
                .orElseThrow(() -> new OrdenNotFoundException("Orden no encontrada con ID: " + id));
        modelMapper.map(ordenDto, ordenExistente);
        Orden ordenActualizada = ordenRepository.save(ordenExistente);
        return modelMapper.map(ordenActualizada, OrdenDto.class);
    }

    @Override
    public List<OrdenDto> listarOrdenes() {
        List<Orden> ordenes = ordenRepository.findAll();
        return ordenes.stream()
                .map(orden -> modelMapper.map(orden, OrdenDto.class))
                .toList();
    }

    @Override
    public OrdenDto obtenerOrdenPorId(Long id) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new OrdenNotFoundException("Orden no encontrada con ID: " + id));
        return modelMapper.map(orden, OrdenDto.class);
    }

    @Override
    public void eliminarOrden(Long id) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new OrdenNotFoundException("Orden no encontrada con ID: " + id));
        ordenRepository.delete(orden);
    }
}
