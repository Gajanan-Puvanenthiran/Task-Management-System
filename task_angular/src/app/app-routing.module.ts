import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/components/login/login.component';
import { SignupComponent } from './auth/components/signup/signup.component';

const routes: Routes = [
  {path:"login", component:LoginComponent},
  {path:"signup", component:SignupComponent},
  {path:"admin", loadChildren:()=>import("./auth/modules/admin/admin.module").then(e=>e.AdminModule)},
  {path:"employee",loadChildren:()=>import("./auth/modules/employee/employee.module").then(e=>e.EmployeeModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
