import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllAchievementsComponent } from './all-achievements.component';

describe('AllAchievementsComponent', () => {
  let component: AllAchievementsComponent;
  let fixture: ComponentFixture<AllAchievementsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllAchievementsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllAchievementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
