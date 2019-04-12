import { Goal } from './goal';
import { UserAchievement } from './user-achievement';

export class UserGoal {
    id: number;
    completed: boolean;
    completedDate: Date;
    userAchievement: UserAchievement;
    goal: Goal;


    constructor(
        id?: number,
        completed?: boolean,
        completedDate?: Date,
        userAchievement?:UserAchievement,
        goal?: Goal,
    ){

    }
}
