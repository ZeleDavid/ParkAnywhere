import { Component, OnInit, ViewChild } from '@angular/core';
import {MatDialog, MatPaginator, MatSort, MatTableDataSource, MatDialogRef } from '@angular/material';
import {Observable} from 'rxjs';
import {map, filter, switchMap, tap, catchError} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import * as _ from 'lodash';
import {DodajComponent} from '../dodaj/dodaj.component';
import {Router} from '@angular/router';
import {AuthService} from '../../shared/services/auth.service';
import * as firebase from 'firebase';
import { HttpHeaders } from '@angular/common/http';

interface ParkirnaHisa {
  cenaNaUro: string;
  idParkirnaHisa: string;
  lastnik: string;
  lat: string;
  lng: string;
  naslov: string;
  naziv: string;
  stVsehMest: string;
  stZasedenihMest: string;
}

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    'Authorization': 'Bearer ' + firebase.auth().currentUser.getIdToken()
  })
};

@Component({
    selector: 'app-tables',
    templateUrl: './tables.component.html',
    styleUrls: ['./tables.component.scss']
})
export class TablesComponent implements OnInit {
    displayedColumns = ['naziv', 'naslov', 'cenaNaUro', 'stZasedenihMest', 'stVsehMest', 'zasedenost', 'lastnik', 'izbrisi'];
    dataSource: MatTableDataSource<ParkirnaHisa>;
    parkirneHise$: Observable<ParkirnaHisa[]>;
    cenaNaUro: number;
    name: string;
    parkirnaHisaZaDodajo: ParkirnaHisa;

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    constructor(private http: HttpClient, public dialog: MatDialog, public authService: AuthService) {
      let parkHise: ParkirnaHisa[] = new Array();
      /*this.http
        .get('http://localhost:8080/Docker_rest/REST/parkirneHise')
        .pipe(
          map(data => _.values(data)),
          tap(parkirneHise => {this.dataSource = new MatTableDataSource(parkirneHise); this.dataSource.paginator = this.paginator;
             this.dataSource.sort = this.sort; } )
        )
        .subscribe(parkirneHise => parkHise = parkirneHise);*/
      // console.log(authService.userData.uid);
      // console.log(firebase.auth().currentUser.uid);
      this.http
        .get('http://45.77.58.205:8000/parkchain/location/' + firebase.auth().currentUser.uid)
        .pipe(
          map(data => _.values(data)),
          tap(parkirneHise => {this.dataSource = new MatTableDataSource(parkirneHise); this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort; } )
        )
        .subscribe(parkirneHise => parkHise = parkirneHise);
    }

    ngOnInit() {


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
    const dialogRef = this.dialog.open(DodajComponent, {
      width: '250px',
      data: { uid: firebase.auth().currentUser.uid, naziv: '', naslov: '', stVsehMest: '',
        stZasedenihMest: 0, cenaNaUro: this.cenaNaUro, lat: 46.559839, lng: 15.638941}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log(result);
      result = { uid: firebase.auth().currentUser.uid, naziv: '', naslov: '', stVsehMest: 400,
        stZasedenihMest: 0, cenaNaUro: 4.5, lat: 46.559839, lng: 15.638941};
      this.dodaj(result)
        .subscribe(odg => console.log(odg));
    });
  }

  dodaj (data: Object): Observable<Object> {
    return this.http.post<Object>('http://45.77.58.205:8000/parkchain/locations/', data, httpOptions)
      .pipe(
      );
  }



  izbrisi(naziv: any) {
    if (confirm('Res želite izbrisati parkirno hišo: ' + naziv + '?')) {
      console.log('Implement delete functionality here');
    }
  }
}

