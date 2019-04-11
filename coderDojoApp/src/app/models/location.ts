import { Address } from './address';

export class Location {
  id: number;
  name: string;
  details: string;
  address: Address;
 constructor(
  id?: number ,
  name: string = '',
  details: string = '',
  address?: {
    id?: number
  }){
    this.id = id;
    this.name = name;
    this.details = details;
    this.address = Object.assign(this, this.address);

  }
}
