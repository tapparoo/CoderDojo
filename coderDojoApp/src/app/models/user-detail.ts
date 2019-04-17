import { User } from './user';
import { Address } from './address';
import { Location } from './location';

export class UserDetail {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  nickname: string;
  phoneNumber: string;
  dob: Date;
  gender: string;
  userImageUrl: string;
  user: User;
  location: Location;
  address: Address;
  achievements: [{}];
  parents: [{}];
  children: [{}];

  constructor(
    nickname: string,
    firstName: string,
    lastName: string,
    email: string,
    phoneNumber: string,
    dob: Date,
    gender: string,
    userImageUrl?: string,
    user?: User,
    location?: {
      id?: number;
      name?: string;
    },
    address?: Address,
    achievements?: [{}],
    parents: [{}] = [{}],
    children: [{}] = [{}],
    id?: number
  ) {
    this.id = id;
    this.nickname = nickname;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.dob = dob;
    this.gender = gender;
    this.userImageUrl = userImageUrl;
    this.user = user;
    this.achievements = achievements;
    this.location = Object.assign({}, this.location);
    this.address = address;
    this.parents = parents;
    this.children = children;
  }
}
