import customer from 'app/entities/customer/customer.reducer';
import order from 'app/entities/order/order.reducer';
import address from 'app/entities/address/address.reducer';
import orderItem from 'app/entities/order-item/order-item.reducer';
import shipment from 'app/entities/shipment/shipment.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  customer,
  order,
  address,
  orderItem,
  shipment,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
