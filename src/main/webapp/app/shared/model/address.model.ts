import { IOrder } from 'app/shared/model/order.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IAddress {
  id?: number;
  line1?: string | null;
  line2?: string | null;
  city?: string | null;
  state?: string | null;
  pincode?: number | null;
  lat?: number | null;
  lon?: number | null;
  country?: string | null;
  landmart?: string | null;
  orders?: IOrder[] | null;
  customer?: ICustomer | null;
}

export const defaultValue: Readonly<IAddress> = {};
