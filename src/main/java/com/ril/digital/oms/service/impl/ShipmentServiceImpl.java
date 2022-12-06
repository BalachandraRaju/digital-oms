package com.ril.digital.oms.service.impl;

import com.ril.digital.oms.domain.Shipment;
import com.ril.digital.oms.repository.ShipmentRepository;
import com.ril.digital.oms.service.ShipmentService;
import com.ril.digital.oms.service.dto.ShipmentDTO;
import com.ril.digital.oms.service.mapper.ShipmentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Shipment}.
 */
@Service
@Transactional
public class ShipmentServiceImpl implements ShipmentService {

    private final Logger log = LoggerFactory.getLogger(ShipmentServiceImpl.class);

    private final ShipmentRepository shipmentRepository;

    private final ShipmentMapper shipmentMapper;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, ShipmentMapper shipmentMapper) {
        this.shipmentRepository = shipmentRepository;
        this.shipmentMapper = shipmentMapper;
    }

    @Override
    public ShipmentDTO save(ShipmentDTO shipmentDTO) {
        log.debug("Request to save Shipment : {}", shipmentDTO);
        Shipment shipment = shipmentMapper.toEntity(shipmentDTO);
        shipment = shipmentRepository.save(shipment);
        return shipmentMapper.toDto(shipment);
    }

    @Override
    public ShipmentDTO update(ShipmentDTO shipmentDTO) {
        log.debug("Request to update Shipment : {}", shipmentDTO);
        Shipment shipment = shipmentMapper.toEntity(shipmentDTO);
        shipment = shipmentRepository.save(shipment);
        return shipmentMapper.toDto(shipment);
    }

    @Override
    public Optional<ShipmentDTO> partialUpdate(ShipmentDTO shipmentDTO) {
        log.debug("Request to partially update Shipment : {}", shipmentDTO);

        return shipmentRepository
            .findById(shipmentDTO.getId())
            .map(existingShipment -> {
                shipmentMapper.partialUpdate(existingShipment, shipmentDTO);

                return existingShipment;
            })
            .map(shipmentRepository::save)
            .map(shipmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShipmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Shipments");
        return shipmentRepository.findAll(pageable).map(shipmentMapper::toDto);
    }

    public Page<ShipmentDTO> findAllWithEagerRelationships(Pageable pageable) {
        return shipmentRepository.findAllWithEagerRelationships(pageable).map(shipmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShipmentDTO> findOne(Long id) {
        log.debug("Request to get Shipment : {}", id);
        return shipmentRepository.findOneWithEagerRelationships(id).map(shipmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Shipment : {}", id);
        shipmentRepository.deleteById(id);
    }
}
