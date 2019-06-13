import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map, filter, switchMap, tap} from 'rxjs/operators';
import {Observable} from 'rxjs';
import * as _ from 'lodash';
import * as firebase from 'firebase';
import {DodajComponent} from '../dodaj/dodaj.component';
import {MatDialog} from '@angular/material';

interface User {
  email: string;
}

@Component({
    selector: 'app-grid',
    templateUrl: './grid.component.html',
    styleUrls: ['./grid.component.scss']
})
export class GridComponent implements OnInit {
    userData: Observable<User[]>;
    podatki: any;
    constructor(private http: HttpClient) {
      // this.podatki = this.getAll();

    }

    getAll() {
      return this.http.get(localStorage.getItem('url') + '/parkchain/user/' + firebase.auth().currentUser.uid).subscribe(data => {
        console.log(data);
        this.podatki = data;
        return data;
      });
    }

    ngOnInit() {
      this.getAll();
    }

  spremeniUrl(value: string) {
    console.log(localStorage.getItem('url'));
    localStorage.setItem('url', value);
    console.log(localStorage.getItem('url'));
  }
}
