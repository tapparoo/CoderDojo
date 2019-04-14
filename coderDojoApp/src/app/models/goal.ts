import { Achievement } from './achievement';
export class Goal {
     id: number;
     name: string;
     description: string;
     achievement: Achievement;


    constructor(
        id?: number,
        name?: string,
        description?: string,
        achievement?: Achievement
    ){
        this.id=id;
        this.name=name;
        this.description=description;
        this.achievement=achievement;
    }
}
