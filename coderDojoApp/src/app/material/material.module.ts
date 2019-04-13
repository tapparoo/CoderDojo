import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatTabsModule} from '@angular/material/tabs';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatTableModule} from '@angular/material/table';
import {MatIconModule} from '@angular/material/icon';
import {MatSortModule} from '@angular/material/sort';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { MatPaginatorModule, MatDialogModule } from '@angular/material';
@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    MatTabsModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatIconModule,
    MatPaginatorModule,
    MatDialogModule,
    MatCheckboxModule,
    MatSortModule
    ],
  exports: [
    MatTabsModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatIconModule,
    MatPaginatorModule,
    MatDialogModule,
    MatCheckboxModule,
    MatSortModule
  ]
})
export class MaterialModule { }
