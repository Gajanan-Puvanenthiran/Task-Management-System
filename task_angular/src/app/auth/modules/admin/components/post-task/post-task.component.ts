import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmployeeModule } from '../../../employee/employee.module';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-task',
  templateUrl: './post-task.component.html',
  styleUrls: ['./post-task.component.scss']
})
export class PostTaskComponent  {

  taskForm!: FormGroup;
  listOfEmployees: any =[];
  listOfPriorities: any =["LOW","MEDIUM","HIGH"];

  constructor(private adminService: AdminService,
    private fb: FormBuilder,
    private snackbar:MatSnackBar,
    private router: Router
  ) {
    this.getUsers();
    this.taskForm=this.fb.group({
      employeeId: [null, [Validators.required]],
      title: [null, [Validators.required]],
      description: [null, [Validators.required]],
      dueDate: [null, [Validators.required]],
      priority: [null, [Validators.required]]
    })
   }

  getUsers(){
    this.adminService.getUsers().subscribe((res)=>{
      this.listOfEmployees=res;
      
    })
  }

  postTask(){
    console.log(this.taskForm.value);
    this.adminService.postTask(this.taskForm.value).subscribe((res)=>{
      if(res.id != null){
        this.snackbar.open("Task posted successful", "Close",{duration:5000});
        this.router.navigateByUrl("/admin/dashboard");
      } else{
        this.snackbar.open("Something went wrong", "Error",{duration:5000});
      }
    })
  }

  

}
