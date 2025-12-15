import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Department {
  idDepartment: number;
  name: string;
  location: string | null;
  phone: string | null;
  head: string | null;
}

@Injectable({ providedIn: 'root' })
export class DepartmentService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Department[]> {
    // URL relative -> passera par Nginx proxy (/student -> spring-service)
    return this.http.get<Department[]>('/student/Department/getAllDepartment');
  }
}
