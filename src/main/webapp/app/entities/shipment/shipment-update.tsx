import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOrderItem } from 'app/shared/model/order-item.model';
import { getEntities as getOrderItems } from 'app/entities/order-item/order-item.reducer';
import { IShipment } from 'app/shared/model/shipment.model';
import { ShipmentType } from 'app/shared/model/enumerations/shipment-type.model';
import { getEntity, updateEntity, createEntity, reset } from './shipment.reducer';

export const ShipmentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const orderItems = useAppSelector(state => state.orderItem.entities);
  const shipmentEntity = useAppSelector(state => state.shipment.entity);
  const loading = useAppSelector(state => state.shipment.loading);
  const updating = useAppSelector(state => state.shipment.updating);
  const updateSuccess = useAppSelector(state => state.shipment.updateSuccess);
  const shipmentTypeValues = Object.keys(ShipmentType);

  const handleClose = () => {
    navigate('/shipment');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getOrderItems({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...shipmentEntity,
      ...values,
      orderItems: mapIdList(values.orderItems),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          type: 'Forward',
          ...shipmentEntity,
          orderItems: shipmentEntity?.orderItems?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="digitalOmsApp.shipment.home.createOrEditLabel" data-cy="ShipmentCreateUpdateHeading">
            <Translate contentKey="digitalOmsApp.shipment.home.createOrEditLabel">Create or edit a Shipment</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="shipment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('digitalOmsApp.shipment.dcLocation')}
                id="shipment-dcLocation"
                name="dcLocation"
                data-cy="dcLocation"
                type="text"
              />
              <ValidatedField label={translate('digitalOmsApp.shipment.type')} id="shipment-type" name="type" data-cy="type" type="select">
                {shipmentTypeValues.map(shipmentType => (
                  <option value={shipmentType} key={shipmentType}>
                    {translate('digitalOmsApp.ShipmentType.' + shipmentType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('digitalOmsApp.shipment.boxSize')}
                id="shipment-boxSize"
                name="boxSize"
                data-cy="boxSize"
                type="text"
              />
              <ValidatedField
                label={translate('digitalOmsApp.shipment.delivery')}
                id="shipment-delivery"
                name="delivery"
                data-cy="delivery"
                type="text"
              />
              <ValidatedField
                label={translate('digitalOmsApp.shipment.tatDate')}
                id="shipment-tatDate"
                name="tatDate"
                data-cy="tatDate"
                type="date"
              />
              <ValidatedField
                label={translate('digitalOmsApp.shipment.tahHourOfDay')}
                id="shipment-tahHourOfDay"
                name="tahHourOfDay"
                data-cy="tahHourOfDay"
                type="text"
              />
              <ValidatedField
                label={translate('digitalOmsApp.shipment.orderItem')}
                id="shipment-orderItem"
                data-cy="orderItem"
                type="select"
                multiple
                name="orderItems"
              >
                <option value="" key="0" />
                {orderItems
                  ? orderItems.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/shipment" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ShipmentUpdate;
