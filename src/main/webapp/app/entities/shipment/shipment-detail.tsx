import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './shipment.reducer';

export const ShipmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const shipmentEntity = useAppSelector(state => state.shipment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="shipmentDetailsHeading">
          <Translate contentKey="digitalOmsApp.shipment.detail.title">Shipment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{shipmentEntity.id}</dd>
          <dt>
            <span id="dcLocation">
              <Translate contentKey="digitalOmsApp.shipment.dcLocation">Dc Location</Translate>
            </span>
          </dt>
          <dd>{shipmentEntity.dcLocation}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="digitalOmsApp.shipment.type">Type</Translate>
            </span>
          </dt>
          <dd>{shipmentEntity.type}</dd>
          <dt>
            <span id="boxSize">
              <Translate contentKey="digitalOmsApp.shipment.boxSize">Box Size</Translate>
            </span>
          </dt>
          <dd>{shipmentEntity.boxSize}</dd>
          <dt>
            <span id="delivery">
              <Translate contentKey="digitalOmsApp.shipment.delivery">Delivery</Translate>
            </span>
          </dt>
          <dd>{shipmentEntity.delivery}</dd>
          <dt>
            <span id="tatDate">
              <Translate contentKey="digitalOmsApp.shipment.tatDate">Tat Date</Translate>
            </span>
          </dt>
          <dd>
            {shipmentEntity.tatDate ? <TextFormat value={shipmentEntity.tatDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="tahHourOfDay">
              <Translate contentKey="digitalOmsApp.shipment.tahHourOfDay">Tah Hour Of Day</Translate>
            </span>
          </dt>
          <dd>{shipmentEntity.tahHourOfDay}</dd>
          <dt>
            <Translate contentKey="digitalOmsApp.shipment.orderItem">Order Item</Translate>
          </dt>
          <dd>
            {shipmentEntity.orderItems
              ? shipmentEntity.orderItems.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {shipmentEntity.orderItems && i === shipmentEntity.orderItems.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/shipment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/shipment/${shipmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ShipmentDetail;
