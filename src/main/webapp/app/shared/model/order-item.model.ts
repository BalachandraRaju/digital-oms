import dayjs from 'dayjs';
import { IOrder } from 'app/shared/model/order.model';
import { IShipment } from 'app/shared/model/shipment.model';
import { ArticleType } from 'app/shared/model/enumerations/article-type.model';
import { OrderItemStatus } from 'app/shared/model/enumerations/order-item-status.model';

export interface IOrderItem {
  id?: number;
  article?: string | null;
  articleType?: ArticleType | null;
  status?: OrderItemStatus | null;
  tatDate?: string | null;
  tahHourOfDay?: number | null;
  order?: IOrder | null;
  shipments?: IShipment[] | null;
}

export const defaultValue: Readonly<IOrderItem> = {};
