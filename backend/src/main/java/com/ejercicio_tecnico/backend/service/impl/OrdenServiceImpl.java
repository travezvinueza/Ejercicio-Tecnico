package com.ejercicio_tecnico.backend.service.impl;

import com.ejercicio_tecnico.backend.dto.OrdenDto;
import com.ejercicio_tecnico.backend.entity.Articulo;
import com.ejercicio_tecnico.backend.entity.Cliente;
import com.ejercicio_tecnico.backend.entity.Orden;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenServiceImpl implements OrdenService {

    private final OrdenRepository ordenRepository;
    private final ClienteRepository clienteRepository;
    private final ArticuloRepository articuloRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrdenDto crearOrden(OrdenDto ordenDto) {
        Cliente cliente = clienteRepository.findById(ordenDto.getClienteId())
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + ordenDto.getClienteId()));

        Orden orden = modelMapper.map(ordenDto, Orden.class);
        orden.setCodigo(generateCodigoUnico());
        orden.setFecha(LocalDate.now());
        orden.setCliente(cliente);

        List<Articulo> articulos = ordenDto.getArticulos().stream().map(articuloDto -> {
            Articulo articulo = modelMapper.map(articuloDto, Articulo.class);
            articulo.setOrden(orden);
            return articulo;
        }).toList();

        orden.setArticulos(articulos);

        Orden nuevaOrden = ordenRepository.save(orden);

        return modelMapper.map(nuevaOrden, OrdenDto.class);
    }

    private String generateCodigoUnico() {
        long ordenCount = ordenRepository.count() + 1;
        return String.format("ORDEN-%06d", ordenCount);
    }

    @Override
    public OrdenDto actualizarOrden(OrdenDto ordenDto) {
        Orden ordenExistente = ordenRepository.findById(ordenDto.getId())
                .orElseThrow(() -> new OrdenNotFoundException("Orden no encontrada con ID: " + ordenDto.getId()));

        Cliente cliente = clienteRepository.findById(ordenDto.getClienteId())
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + ordenDto.getClienteId()));
        ordenExistente.setCliente(cliente);

        ordenExistente.getArticulos().clear();

        List<Articulo> articulosActualizados = ordenDto.getArticulos().stream().map(articuloDto -> {
            Articulo articulo = modelMapper.map(articuloDto, Articulo.class);
            articulo.setOrden(ordenExistente);
            return articulo;
        }).toList();

        ordenExistente.setArticulos(articulosActualizados);

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
