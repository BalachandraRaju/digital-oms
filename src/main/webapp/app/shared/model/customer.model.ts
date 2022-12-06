import { IOrder } from 'app/shared/model/order.model';
import { IAddress } from 'app/shared/model/address.model';

export interface ICustomer {
  id?: number;
  name?: string | null;
  email?: string | null;
  phone?: number;
  orders?: IOrder[] | null;
  addresses?: IAddress[] | null;
}

export const defaultValue: Readonly<ICustomer> = {};
