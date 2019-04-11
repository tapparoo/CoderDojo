export class Meeting {
  id: number;
  name: string;
  scheduledTime: string;
 // location: Location;
 constructor(
  id?: number ,
  name: string = '',
  scheduledTime: string = ''){
    this.id = id;
    this.name = name;
    this.scheduledTime = scheduledTime;
  }
}
