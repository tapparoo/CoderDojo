import { TestBed } from '@angular/core/testing';

import { UserGoalService } from './user-goal.service';

describe('UserGoalService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UserGoalService = TestBed.get(UserGoalService);
    expect(service).toBeTruthy();
  });
});
