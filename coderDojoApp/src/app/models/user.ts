export class User {
  id: number;
  username: string;
  password; string;
  enabled: boolean;
  roles: [];

  constructor(
    username: string,
    password: string,
    enabled: boolean,
    roles?: [],
    id?: number
  ) {
    this.username = username;
    this.password = password;
    this.enabled = enabled;
    this.id = id;
    this.roles = roles;
  }
}
