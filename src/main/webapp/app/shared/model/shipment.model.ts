import dayjs from 'dayjs';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { ShipmentType } from 'app/shared/model/enumerations/shipment-type.model';

export interface IShipment {
  id?: number;
  dcLocation?: string | null;
  type?: ShipmentType | null;
  boxSize?: string | null;
  delivery?: string | null;
  tatDate?: string | null;
  tahHourOfDay?: number | null;
  orderItems?: IOrderItem[] | null;
}

export const defaultValue: Readonly<IShipment> = {};
