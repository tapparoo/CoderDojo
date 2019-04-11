export class Meeting {
  id: number;
  name: string;
  scheduledTime: string;
  location: Location;
 constructor(
  id?: number ,
  name: string = '',
  scheduledTime: string = '',
  location?: {
    id?: number
  }){
    this.id = id;
    this.name = name;
    this.scheduledTime = scheduledTime;
    this.location = Object.assign(this, this.location);

  }
}
