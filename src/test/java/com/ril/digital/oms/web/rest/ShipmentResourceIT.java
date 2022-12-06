package com.ril.digital.oms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ril.digital.oms.IntegrationTest;
import com.ril.digital.oms.domain.Shipment;
import com.ril.digital.oms.domain.enumeration.ShipmentType;
import com.ril.digital.oms.repository.ShipmentRepository;
import com.ril.digital.oms.service.ShipmentService;
import com.ril.digital.oms.service.dto.ShipmentDTO;
import com.ril.digital.oms.service.mapper.ShipmentMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ShipmentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ShipmentResourceIT {

    private static final String DEFAULT_DC_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_DC_LOCATION = "BBBBBBBBBB";

    private static final ShipmentType DEFAULT_TYPE = ShipmentType.Forward;
    private static final ShipmentType UPDATED_TYPE = ShipmentType.Return;

    private static final String DEFAULT_BOX_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_BOX_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TAT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TAT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_TAH_HOUR_OF_DAY = 1;
    private static final Integer UPDATED_TAH_HOUR_OF_DAY = 2;

    private static final String ENTITY_API_URL = "/api/shipments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Mock
    private ShipmentRepository shipmentRepositoryMock;

    @Autowired
    private ShipmentMapper shipmentMapper;

    @Mock
    private ShipmentService shipmentServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShipmentMockMvc;

    private Shipment shipment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shipment createEntity(EntityManager em) {
        Shipment shipment = new Shipment()
            .dcLocation(DEFAULT_DC_LOCATION)
            .type(DEFAULT_TYPE)
            .boxSize(DEFAULT_BOX_SIZE)
            .delivery(DEFAULT_DELIVERY)
            .tatDate(DEFAULT_TAT_DATE)
            .tahHourOfDay(DEFAULT_TAH_HOUR_OF_DAY);
        return shipment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shipment createUpdatedEntity(EntityManager em) {
        Shipment shipment = new Shipment()
            .dcLocation(UPDATED_DC_LOCATION)
            .type(UPDATED_TYPE)
            .boxSize(UPDATED_BOX_SIZE)
            .delivery(UPDATED_DELIVERY)
            .tatDate(UPDATED_TAT_DATE)
            .tahHourOfDay(UPDATED_TAH_HOUR_OF_DAY);
        return shipment;
    }

    @BeforeEach
    public void initTest() {
        shipment = createEntity(em);
    }

    @Test
    @Transactional
    void createShipment() throws Exception {
        int databaseSizeBeforeCreate = shipmentRepository.findAll().size();
        // Create the Shipment
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(shipment);
        restShipmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeCreate + 1);
        Shipment testShipment = shipmentList.get(shipmentList.size() - 1);
        assertThat(testShipment.getDcLocation()).isEqualTo(DEFAULT_DC_LOCATION);
        assertThat(testShipment.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testShipment.getBoxSize()).isEqualTo(DEFAULT_BOX_SIZE);
        assertThat(testShipment.getDelivery()).isEqualTo(DEFAULT_DELIVERY);
        assertThat(testShipment.getTatDate()).isEqualTo(DEFAULT_TAT_DATE);
        assertThat(testShipment.getTahHourOfDay()).isEqualTo(DEFAULT_TAH_HOUR_OF_DAY);
    }

    @Test
    @Transactional
    void createShipmentWithExistingId() throws Exception {
        // Create the Shipment with an existing ID
        shipment.setId(1L);
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(shipment);

        int databaseSizeBeforeCreate = shipmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllShipments() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        // Get all the shipmentList
        restShipmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipment.getId().intValue())))
            .andExpect(jsonPath("$.[*].dcLocation").value(hasItem(DEFAULT_DC_LOCATION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].boxSize").value(hasItem(DEFAULT_BOX_SIZE)))
            .andExpect(jsonPath("$.[*].delivery").value(hasItem(DEFAULT_DELIVERY)))
            .andExpect(jsonPath("$.[*].tatDate").value(hasItem(DEFAULT_TAT_DATE.toString())))
            .andExpect(jsonPath("$.[*].tahHourOfDay").value(hasItem(DEFAULT_TAH_HOUR_OF_DAY)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllShipmentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(shipmentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restShipmentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(shipmentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllShipmentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(shipmentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restShipmentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(shipmentRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getShipment() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        // Get the shipment
        restShipmentMockMvc
            .perform(get(ENTITY_API_URL_ID, shipment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shipment.getId().intValue()))
            .andExpect(jsonPath("$.dcLocation").value(DEFAULT_DC_LOCATION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.boxSize").value(DEFAULT_BOX_SIZE))
            .andExpect(jsonPath("$.delivery").value(DEFAULT_DELIVERY))
            .andExpect(jsonPath("$.tatDate").value(DEFAULT_TAT_DATE.toString()))
            .andExpect(jsonPath("$.tahHourOfDay").value(DEFAULT_TAH_HOUR_OF_DAY));
    }

    @Test
    @Transactional
    void getNonExistingShipment() throws Exception {
        // Get the shipment
        restShipmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingShipment() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();

        // Update the shipment
        Shipment updatedShipment = shipmentRepository.findById(shipment.getId()).get();
        // Disconnect from session so that the updates on updatedShipment are not directly saved in db
        em.detach(updatedShipment);
        updatedShipment
            .dcLocation(UPDATED_DC_LOCATION)
            .type(UPDATED_TYPE)
            .boxSize(UPDATED_BOX_SIZE)
            .delivery(UPDATED_DELIVERY)
            .tatDate(UPDATED_TAT_DATE)
            .tahHourOfDay(UPDATED_TAH_HOUR_OF_DAY);
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(updatedShipment);

        restShipmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
        Shipment testShipment = shipmentList.get(shipmentList.size() - 1);
        assertThat(testShipment.getDcLocation()).isEqualTo(UPDATED_DC_LOCATION);
        assertThat(testShipment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testShipment.getBoxSize()).isEqualTo(UPDATED_BOX_SIZE);
        assertThat(testShipment.getDelivery()).isEqualTo(UPDATED_DELIVERY);
        assertThat(testShipment.getTatDate()).isEqualTo(UPDATED_TAT_DATE);
        assertThat(testShipment.getTahHourOfDay()).isEqualTo(UPDATED_TAH_HOUR_OF_DAY);
    }

    @Test
    @Transactional
    void putNonExistingShipment() throws Exception {
        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();
        shipment.setId(count.incrementAndGet());

        // Create the Shipment
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(shipment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchShipment() throws Exception {
        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();
        shipment.setId(count.incrementAndGet());

        // Create the Shipment
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(shipment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamShipment() throws Exception {
        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();
        shipment.setId(count.incrementAndGet());

        // Create the Shipment
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(shipment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipmentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateShipmentWithPatch() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();

        // Update the shipment using partial update
        Shipment partialUpdatedShipment = new Shipment();
        partialUpdatedShipment.setId(shipment.getId());

        partialUpdatedShipment
            .dcLocation(UPDATED_DC_LOCATION)
            .type(UPDATED_TYPE)
            .delivery(UPDATED_DELIVERY)
            .tahHourOfDay(UPDATED_TAH_HOUR_OF_DAY);

        restShipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShipment))
            )
            .andExpect(status().isOk());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
        Shipment testShipment = shipmentList.get(shipmentList.size() - 1);
        assertThat(testShipment.getDcLocation()).isEqualTo(UPDATED_DC_LOCATION);
        assertThat(testShipment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testShipment.getBoxSize()).isEqualTo(DEFAULT_BOX_SIZE);
        assertThat(testShipment.getDelivery()).isEqualTo(UPDATED_DELIVERY);
        assertThat(testShipment.getTatDate()).isEqualTo(DEFAULT_TAT_DATE);
        assertThat(testShipment.getTahHourOfDay()).isEqualTo(UPDATED_TAH_HOUR_OF_DAY);
    }

    @Test
    @Transactional
    void fullUpdateShipmentWithPatch() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();

        // Update the shipment using partial update
        Shipment partialUpdatedShipment = new Shipment();
        partialUpdatedShipment.setId(shipment.getId());

        partialUpdatedShipment
            .dcLocation(UPDATED_DC_LOCATION)
            .type(UPDATED_TYPE)
            .boxSize(UPDATED_BOX_SIZE)
            .delivery(UPDATED_DELIVERY)
            .tatDate(UPDATED_TAT_DATE)
            .tahHourOfDay(UPDATED_TAH_HOUR_OF_DAY);

        restShipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShipment))
            )
            .andExpect(status().isOk());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
        Shipment testShipment = shipmentList.get(shipmentList.size() - 1);
        assertThat(testShipment.getDcLocation()).isEqualTo(UPDATED_DC_LOCATION);
        assertThat(testShipment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testShipment.getBoxSize()).isEqualTo(UPDATED_BOX_SIZE);
        assertThat(testShipment.getDelivery()).isEqualTo(UPDATED_DELIVERY);
        assertThat(testShipment.getTatDate()).isEqualTo(UPDATED_TAT_DATE);
        assertThat(testShipment.getTahHourOfDay()).isEqualTo(UPDATED_TAH_HOUR_OF_DAY);
    }

    @Test
    @Transactional
    void patchNonExistingShipment() throws Exception {
        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();
        shipment.setId(count.incrementAndGet());

        // Create the Shipment
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(shipment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shipmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchShipment() throws Exception {
        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();
        shipment.setId(count.incrementAndGet());

        // Create the Shipment
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(shipment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamShipment() throws Exception {
        int databaseSizeBeforeUpdate = shipmentRepository.findAll().size();
        shipment.setId(count.incrementAndGet());

        // Create the Shipment
        ShipmentDTO shipmentDTO = shipmentMapper.toDto(shipment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(shipmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Shipment in the database
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteShipment() throws Exception {
        // Initialize the database
        shipmentRepository.saveAndFlush(shipment);

        int databaseSizeBeforeDelete = shipmentRepository.findAll().size();

        // Delete the shipment
        restShipmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, shipment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Shipment> shipmentList = shipmentRepository.findAll();
        assertThat(shipmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
