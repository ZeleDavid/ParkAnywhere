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
  ParkHouseId: string;
  uid: string;
  lat: string;
  lng: string;
  naslov: string;
  naziv: string;
  tip: string;
  nacinPlacila: string;
  stVsehMest: string;
  stZasedenihMest: string;
}

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    'Authorization': 'Bearer ' + firebase.auth().currentUser.getIdToken(),
  })
};

@Component({
    selector: 'app-tables',
    templateUrl: './tables.component.html',
    styleUrls: ['./tables.component.scss']
})
export class TablesComponent implements OnInit {
    displayedColumns = ['naziv', 'tip', 'naslov', 'cenaNaUro', 'nacinPlacila', 'stZasedenihMest', 'stVsehMest', 'zasedenost', 'izbrisi'];
    dataSource: MatTableDataSource<ParkirnaHisa>;
    name: string;

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    constructor(private http: HttpClient, public dialog: MatDialog, public authService: AuthService) {
      this.naloziPodatke();
    }

    ngOnInit() {


    }

    naloziPodatke() {
      let parkHise: ParkirnaHisa[] = new Array();
      this.http
        .get(localStorage.getItem('url') + '/parkchain/location/' + firebase.auth().currentUser.uid)
        .pipe(
          map(data => _.values(data)),
          tap(parkirneHise => {this.dataSource = new MatTableDataSource(parkirneHise); this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort; } )
        )
        .subscribe(parkirneHise => parkHise = parkirneHise);
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
      width: '550px',
      data: { uid: firebase.auth().currentUser.uid, naziv: '', naslov: '', stVsehMest: '',
        stZasedenihMest: 0, cenaNaUro: '', lat: 0.0, lng: 0.0, tip: '', nacinPlacila: ''}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.http
        .get('https://eu1.locationiq.com/v1/search.php?key=ecd07a8f9f191f&q=' + result.naslov + '%20maribor&format=json')
        .pipe(
          map(data => _.values(data)),
          tap(lokacije => { result.lat = lokacije [0].lat; result.lng = lokacije [0].lon; } )
        )
        .subscribe( lokacije => { result.lat = Number(lokacije [0].lat); result.lng = Number(lokacije [0].lon);
        result.stVsehMest = Number(result.stVsehMest); result.cenaNaUro = Number(result.cenaNaUro);
        this.dodaj(result)
          .subscribe( odg => this.naloziPodatke()); });
      // result = { uid: firebase.auth().currentUser.uid, naziv: '', naslov: '', stVsehMest: 400,
        // stZasedenihMest: 0, cenaNaUro: 4.5, lat: 46.559839, lng: 15.638941};
      // this.dodaj(result)
      //  .subscribe( odg => this.naloziPodatke());
    });
  }

  dodaj (data: Object): Observable<Object> {
    return this.http.post<Object>(localStorage.getItem('url') + '/parkchain/locations', data, httpOptions)
      .pipe(
      );
  }

  brisiParkHiso (id: number, uid: number): Observable<{}> {
    const url = localStorage.getItem('url') + '/parkchain/location/' + uid + '/' + id;
    return this.http.delete(url, httpOptions)
      .pipe(
      );
  }



  izbrisi(obj: any) {
    if (confirm('Res želite izbrisati parkirno hišo: ' + obj.naziv + '?')) {
      this.brisiParkHiso(obj.ParkHouseId, obj.uid).subscribe( odg => this.naloziPodatke());
    }
  }
}

