import { Component, OnInit, ViewChild } from '@angular/core';
import {MatDialog, MatPaginator, MatSort, MatTableDataSource, MatDialogRef } from '@angular/material';
import {Observable} from 'rxjs';
import {map, filter, switchMap, tap} from 'rxjs/operators';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import * as _ from 'lodash';
import {DodajUComponent} from '../dodajU/dodajU.component';
import * as firebase from 'firebase';

interface User {
  uid: string;
  email: string;
  password: string;
  walletAddress: string;
  isAdmin: boolean;
}

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    'Authorization': 'Bearer ' + firebase.auth().currentUser.getIdToken(),
  })
};

@Component({
    selector: 'app-tables',
    templateUrl: './uporabniki.component.html',
    styleUrls: ['./uporabniki.component.scss']
})
export class UporabnikiComponent implements OnInit {
    displayedColumns = ['email', 'walletAddress', 'isAdmin', 'izbrisi'];
    dataSource: MatTableDataSource<User>;
    name: string;

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    constructor(private http: HttpClient, public dialog: MatDialog) {
      this.naloziPodatke();
    }

    ngOnInit() {
    }

    naloziPodatke() {
      let userji: User[] = new Array();
      this.http
        .get(localStorage.getItem('url') + '/parkchain/users')
        .pipe(
          map(data => _.values(data)),
          tap(users => {this.dataSource = new MatTableDataSource(users[0]); this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort; } )
        )
        .subscribe(users => userji = users);
    }

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.dataSource.filter = filterValue;
        if (this.dataSource.paginator) {
            this.dataSource.paginator.firstPage();
        }
    }

  openDialog(): void {
    const dialogRef = this.dialog.open(DodajUComponent, {
      width: '380px',
      data: { email: '', password: '', walletAddress: '', isAdmin: false }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.dodaj(result)
        .subscribe( odg => this.naloziPodatke());
    });
  }

  dodaj (data: Object): Observable<Object> {
    this.naloziPodatke();
    return this.http.post<Object>(localStorage.getItem('url') + '/parkchain/user', data, httpOptions)
      .pipe(
      );
  }

  brisiUsera (uid: number): Observable<{}> {
    const url = localStorage.getItem('url') + '/parkchain/user/' + uid;
    return this.http.delete(url, httpOptions)
      .pipe(
      );
  }

  izbrisi(obj: any) {
    if (confirm('Res želite izbrisati uporabnika z emailom: ' + obj.email + '?')) {
      this.brisiUsera(obj.uid).subscribe(odg => this.naloziPodatke());
    }
  }
}

