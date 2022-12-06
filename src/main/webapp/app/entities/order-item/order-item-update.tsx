import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOrder } from 'app/shared/model/order.model';
import { getEntities as getOrders } from 'app/entities/order/order.reducer';
import { IShipment } from 'app/shared/model/shipment.model';
import { getEntities as getShipments } from 'app/entities/shipment/shipment.reducer';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { ArticleType } from 'app/shared/model/enumerations/article-type.model';
import { OrderItemStatus } from 'app/shared/model/enumerations/order-item-status.model';
import { getEntity, updateEntity, createEntity, reset } from './order-item.reducer';

export const OrderItemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const orders = useAppSelector(state => state.order.entities);
  const shipments = useAppSelector(state => state.shipment.entities);
  const orderItemEntity = useAppSelector(state => state.orderItem.entity);
  const loading = useAppSelector(state => state.orderItem.loading);
  const updating = useAppSelector(state => state.orderItem.updating);
  const updateSuccess = useAppSelector(state => state.orderItem.updateSuccess);
  const articleTypeValues = Object.keys(ArticleType);
  const orderItemStatusValues = Object.keys(OrderItemStatus);

  const handleClose = () => {
    navigate('/order-item');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getOrders({}));
    dispatch(getShipments({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...orderItemEntity,
      ...values,
      order: orders.find(it => it.id.toString() === values.order.toString()),
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
          articleType: 'GnG',
          status: 'Created',
          ...orderItemEntity,
          order: orderItemEntity?.order?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="digitalOmsApp.orderItem.home.createOrEditLabel" data-cy="OrderItemCreateUpdateHeading">
            <Translate contentKey="digitalOmsApp.orderItem.home.createOrEditLabel">Create or edit a OrderItem</Translate>
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
                  id="order-item-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('digitalOmsApp.orderItem.article')}
                id="order-item-article"
                name="article"
                data-cy="article"
                type="text"
              />
              <ValidatedField
                label={translate('digitalOmsApp.orderItem.articleType')}
                id="order-item-articleType"
                name="articleType"
                data-cy="articleType"
                type="select"
              >
                {articleTypeValues.map(articleType => (
                  <option value={articleType} key={articleType}>
                    {translate('digitalOmsApp.ArticleType.' + articleType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('digitalOmsApp.orderItem.status')}
                id="order-item-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {orderItemStatusValues.map(orderItemStatus => (
                  <option value={orderItemStatus} key={orderItemStatus}>
                    {translate('digitalOmsApp.OrderItemStatus.' + orderItemStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('digitalOmsApp.orderItem.tatDate')}
                id="order-item-tatDate"
                name="tatDate"
                data-cy="tatDate"
                type="date"
              />
              <ValidatedField
                label={translate('digitalOmsApp.orderItem.tahHourOfDay')}
                id="order-item-tahHourOfDay"
                name="tahHourOfDay"
                data-cy="tahHourOfDay"
                type="text"
              />
              <ValidatedField
                id="order-item-order"
                name="order"
                data-cy="order"
                label={translate('digitalOmsApp.orderItem.order')}
                type="select"
              >
                <option value="" key="0" />
                {orders
                  ? orders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/order-item" replace color="info">
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

export default OrderItemUpdate;
