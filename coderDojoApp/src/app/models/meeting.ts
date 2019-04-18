import { Location } from './location';
export class Meeting {
  public id: number;
  public name: string;
  public scheduledTime: Date;
  // public meetingAttendees: [];
  public location: Location;
  constructor(
    name: string = '',
    scheduledTime: Date,
    id?: number,
    // meetingAttendees?: [],
    location?: {
      id?: number;
      name?: string;
    }
  ) {
    this.id = id;
    this.name = name;
    this.scheduledTime = scheduledTime;
    // this.meetingAttendees = meetingAttendees;
    this.location = Object.assign({}, this.location);
  }
}
