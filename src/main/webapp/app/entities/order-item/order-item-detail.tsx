import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './order-item.reducer';

export const OrderItemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const orderItemEntity = useAppSelector(state => state.orderItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderItemDetailsHeading">
          <Translate contentKey="digitalOmsApp.orderItem.detail.title">OrderItem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.id}</dd>
          <dt>
            <span id="article">
              <Translate contentKey="digitalOmsApp.orderItem.article">Article</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.article}</dd>
          <dt>
            <span id="articleType">
              <Translate contentKey="digitalOmsApp.orderItem.articleType">Article Type</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.articleType}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="digitalOmsApp.orderItem.status">Status</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.status}</dd>
          <dt>
            <span id="tatDate">
              <Translate contentKey="digitalOmsApp.orderItem.tatDate">Tat Date</Translate>
            </span>
          </dt>
          <dd>
            {orderItemEntity.tatDate ? <TextFormat value={orderItemEntity.tatDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="tahHourOfDay">
              <Translate contentKey="digitalOmsApp.orderItem.tahHourOfDay">Tah Hour Of Day</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.tahHourOfDay}</dd>
          <dt>
            <Translate contentKey="digitalOmsApp.orderItem.order">Order</Translate>
          </dt>
          <dd>{orderItemEntity.order ? orderItemEntity.order.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/order-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order-item/${orderItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrderItemDetail;
