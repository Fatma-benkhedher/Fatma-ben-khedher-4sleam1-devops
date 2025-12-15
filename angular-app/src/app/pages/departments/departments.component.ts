import { Component, OnInit } from '@angular/core';
import { Department, DepartmentService } from '../../services/department.service';

@Component({
  selector: 'app-departments',
  templateUrl: './departments.component.html'
})
export class DepartmentsComponent implements OnInit {
  departments: Department[] = [];
  loading = true;
  error = '';

  constructor(private depService: DepartmentService) {}

  ngOnInit(): void {
    this.depService.getAll().subscribe({
      next: (res) => {
        this.departments = res;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des départements';
        this.loading = false;
        console.error(err);
      }
    });
  }
}
