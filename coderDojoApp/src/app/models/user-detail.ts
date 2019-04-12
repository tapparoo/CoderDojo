export class UserDetail {
  id: number;
  nickname: string;
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
    address: {} = {},
    parents: [{}] = [{}],
    children: [{}] = [{}],
    id?: number
  ) {
    this.id = id;
    this.nickname = nickname;
    this.phoneNumber = phoneNumber;
    this.dob = dob;
    this.location = location;
    this.address = address;
    this.parents = parents;
    this.children = children;
  }
}
