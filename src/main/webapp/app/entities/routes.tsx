import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Customer from './customer';
import Order from './order';
import Address from './address';
import OrderItem from './order-item';
import Shipment from './shipment';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="customer/*" element={<Customer />} />
        <Route path="order/*" element={<Order />} />
        <Route path="address/*" element={<Address />} />
        <Route path="order-item/*" element={<OrderItem />} />
        <Route path="shipment/*" element={<Shipment />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
