import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { PostTaskComponent } from './components/post-task/post-task.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from 'src/app/material.module';
import { UpdateTaskComponent } from './components/update-task/update-task.component';
import { ViewTaskDetailsComponent } from './components/view-task-details/view-task-details.component';


@NgModule({
  declarations: [
    DashboardComponent,
    PostTaskComponent,
    UpdateTaskComponent,
    ViewTaskDetailsComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule,
    ReactiveFormsModule,HttpClientModule,
    MaterialModule
  ]
})
export class AdminModule { }
