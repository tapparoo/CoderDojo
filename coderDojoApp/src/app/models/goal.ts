import { Achievement } from './achievement';
export class Goal {
    private id: number;
    private name: string;
    private description: string;
    private achievement: Achievement;


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
