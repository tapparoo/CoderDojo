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
  location: {};
  address: {};
  parents: [{}];
  children: [{}];

  constructor(
    nickname: string = '',
    firstName: string,
    lastName: string,
    email: string,
    phoneNumber: string = '',
    dob: Date = new Date(),
    gender: string,
    userImageUrl: string,
    location: {} = {},
    address: {} = {},
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
    this.location = location;
    this.address = address;
    this.parents = parents;
    this.children = children;
  }
}
