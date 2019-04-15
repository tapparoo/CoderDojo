export class GoalDTO {
    id: number;
    name: string;
    description: string;
    achievementId: number;

    constructor(id: number,
        name: string,
        description: string,
        achievementId: number,
    ) {
        this.id=id;
        this.name = name;
        this.description = description;
        this.achievementId = achievementId;
    }
}
