package com.ejercicio_tecnico.backend.service.impl;

import com.ejercicio_tecnico.backend.dto.ArticuloDto;
import com.ejercicio_tecnico.backend.entity.Articulo;
import com.ejercicio_tecnico.backend.entity.Orden;
import com.ejercicio_tecnico.backend.exception.ArticuloNotFoundException;
import com.ejercicio_tecnico.backend.exception.OrdenNotFoundException;
import com.ejercicio_tecnico.backend.repository.ArticuloRepository;
import com.ejercicio_tecnico.backend.repository.OrdenRepository;
import com.ejercicio_tecnico.backend.service.ArticuloService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticuloServiceImpl implements ArticuloService {

    private final ArticuloRepository articuloRepository;
    private final ModelMapper modelMapper;

    @Override
    public ArticuloDto crearArticulo(ArticuloDto articuloDto) {
        if (articuloRepository.existsByNombre(articuloDto.getNombre())) {
            throw new IllegalArgumentException("El articulo con el nombre " + articuloDto.getNombre() + " ya existe.");
        }

        Articulo articulo = modelMapper.map(articuloDto, Articulo.class);
        articulo.setCodigo(generateCodigoUnico());

        Articulo nuevoArticulo = articuloRepository.save(articulo);
        return modelMapper.map(nuevoArticulo, ArticuloDto.class);
    }

    private String generateCodigoUnico() {
        long articuloCount = articuloRepository.count() + 1;
        return String.format("ARTICULO-%06d", articuloCount);
    }

    @Override
    public ArticuloDto actualizarArticulo(ArticuloDto articuloDto) {
        Articulo articulo = articuloRepository.findById(articuloDto.getId())
                .orElseThrow(() -> new ArticuloNotFoundException("Artículo no encontrado con ID: " + articuloDto.getId()));

        articulo.setCodigo(articuloDto.getCodigo());
        articulo.setNombre(articuloDto.getNombre());
        articulo.setPrecioUnitario(articuloDto.getPrecioUnitario());

        Articulo articuloActualizado = articuloRepository.save(articulo);
        return modelMapper.map(articuloActualizado, ArticuloDto.class);
    }


    @Override
    public List<ArticuloDto> listarArticulos() {
        List<Articulo> articulos = articuloRepository.findAll();
        return articulos.stream()
                .map(articulo -> modelMapper.map(articulo, ArticuloDto.class))
                .toList();
    }

    @Override
    public ArticuloDto obtenerArticuloPorId(Long id) {
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new ArticuloNotFoundException("Artículo no encontrado con ID: " + id));
        return modelMapper.map(articulo, ArticuloDto.class);
    }

    @Override
    public void eliminarArticulo(Long id) {
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new ArticuloNotFoundException("Artículo no encontrado con ID: " + id));
        articuloRepository.delete(articulo);
    }
}
