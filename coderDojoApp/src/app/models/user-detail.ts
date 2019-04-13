export class UserDetail {
  id: number;
  nickname: string;
  firstName: string;
  lastName: string;
  email: string;
  gender: string;
  userImageUrl: string;
  phoneNumber: string;
  dob: Date;
  location: {};
  address: {};
  parents: [{}];
  children: [{}];

  constructor(
    nickname: string = '',
    phoneNumber: string = '',
    dob: Date,
    location: {} = {},
    userImageUrl?: string ,
    firstName?: string ,
    lastName?: string ,
    gender?: string ,
    // address?: {} = {},
    // parents?: [{}] = [{}],
    // children?: [{}] = [{}],
    email?: string,
    id?: number
  ) {
    this.id = id;
    this.nickname = nickname;
    this.phoneNumber = phoneNumber;
    this.dob = dob;
    this.location = location;
    // this.address = address;
    // this.parents = parents;
    // this.children = children;
    this.firstName = firstName;
    this.lastName = lastName ;
    this.email = email;
    this.gender = gender;
    this.userImageUrl = userImageUrl;
  }
}
