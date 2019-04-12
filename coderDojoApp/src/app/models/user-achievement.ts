import { Achievement } from './achievement';
import { UserGoal } from './user-goal';
export class UserAchievement {
    id: number;
    achieved: boolean;
    achievedDate: Date;
    achievement: Achievement;
    goals: UserGoal[];
    // userDetail: UserDetail;

    constructor(
        id: number,
        achieved: boolean,
        achievedDate: Date,
        achievement: Achievement,
        goals: UserGoal[],
        // userDetail: UserDetail
    ){
        this.id=id;
        this.achieved=achieved;
        this.achievedDate=achievedDate;
        this.achieved=achieved;
        this.goals=goals;
        // this.userDetail=userDetail;
    }

}
