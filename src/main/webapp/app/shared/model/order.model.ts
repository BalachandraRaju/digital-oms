import { IOrderItem } from 'app/shared/model/order-item.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { IAddress } from 'app/shared/model/address.model';
import { Channel } from 'app/shared/model/enumerations/channel.model';

export interface IOrder {
  id?: number;
  channel?: Channel | null;
  orderItems?: IOrderItem[] | null;
  customer?: ICustomer | null;
  address?: IAddress | null;
}

export const defaultValue: Readonly<IOrder> = {};
