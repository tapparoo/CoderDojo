import { Goal } from './goal';
export class Achievement {
     id: number;
     name: string;
     description: string;
     imageUrl: string;
     goals: Goal[];


    constructor(
        id?: number,
        name?: string,
        description?: string,
        imageUrl?:string,
        goals?: Goal[]
    ){
        this.id=id;
        this.name=name;
        this.description=description;
        this.imageUrl = imageUrl;
        this.goals = goals;
    };
}
