import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './address.reducer';

export const AddressDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const addressEntity = useAppSelector(state => state.address.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="addressDetailsHeading">
          <Translate contentKey="digitalOmsApp.address.detail.title">Address</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{addressEntity.id}</dd>
          <dt>
            <span id="line1">
              <Translate contentKey="digitalOmsApp.address.line1">Line 1</Translate>
            </span>
          </dt>
          <dd>{addressEntity.line1}</dd>
          <dt>
            <span id="line2">
              <Translate contentKey="digitalOmsApp.address.line2">Line 2</Translate>
            </span>
          </dt>
          <dd>{addressEntity.line2}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="digitalOmsApp.address.city">City</Translate>
            </span>
          </dt>
          <dd>{addressEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="digitalOmsApp.address.state">State</Translate>
            </span>
          </dt>
          <dd>{addressEntity.state}</dd>
          <dt>
            <span id="pincode">
              <Translate contentKey="digitalOmsApp.address.pincode">Pincode</Translate>
            </span>
          </dt>
          <dd>{addressEntity.pincode}</dd>
          <dt>
            <span id="lat">
              <Translate contentKey="digitalOmsApp.address.lat">Lat</Translate>
            </span>
          </dt>
          <dd>{addressEntity.lat}</dd>
          <dt>
            <span id="lon">
              <Translate contentKey="digitalOmsApp.address.lon">Lon</Translate>
            </span>
          </dt>
          <dd>{addressEntity.lon}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="digitalOmsApp.address.country">Country</Translate>
            </span>
          </dt>
          <dd>{addressEntity.country}</dd>
          <dt>
            <span id="landmart">
              <Translate contentKey="digitalOmsApp.address.landmart">Landmart</Translate>
            </span>
          </dt>
          <dd>{addressEntity.landmart}</dd>
          <dt>
            <Translate contentKey="digitalOmsApp.address.customer">Customer</Translate>
          </dt>
          <dd>{addressEntity.customer ? addressEntity.customer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/address/${addressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AddressDetail;
