import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import {Observable} from 'rxjs';
import { map, filter, switchMap } from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import * as _ from 'lodash';
import * as firebase from 'firebase';
declare let L;


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


export interface PeriodicElement {
    name: string;
    position: number;
    weight: number;
    symbol: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
    { position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H' },
    { position: 2, name: 'Helium', weight: 4.0026, symbol: 'He' },
    { position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li' },
    { position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be' },
    { position: 5, name: 'Boron', weight: 10.811, symbol: 'B' },
    { position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C' },
    { position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N' }
];

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
    displayedColumns = ['position', 'name', 'weight', 'symbol'];
    dataSource = new MatTableDataSource(ELEMENT_DATA);
    places: Array<any> = [];
    parkirneHise$: Observable<ParkirnaHisa[]>;

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
        this.dataSource.filter = filterValue;
    }

    constructor(private http: HttpClient ) {
        this.parkirneHise$ = this.http
        .get<ParkirnaHisa[]>('http://45.77.58.205:8000/parkchain/location/' + firebase.auth().currentUser.uid)
          .pipe(map(data => _.values(data)))
        ;
    }

    ngOnInit() {
      let map1 = L.map('map').setView([46.560630, 15.632039], 15);
      if (this.parkirneHise$[0] != null) {
        map1 = L.map('map').setView([this.parkirneHise$[0].lat, this.parkirneHise$[0].lng], 15);
        console.log(this.parkirneHise$[0]);
      }
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
      }).addTo(map1);

      const MIcon = L.icon({
        // tslint:disable-next-line:max-line-length
     // iconUrl: 'https://cdn0.iconfinder.com/data/icons/travel-vacation/289/travel-transport-hotel-vacation-holidays-tourist-tourism-travelling-traveling_164-512.png',
        iconUrl: 'https://raw.githubusercontent.com/ZeleDavid/ParkAnywhere/master/Documentation/Pin-slike/m.png',
      iconSize:     [40, 40], // size of the icon
      // shadowSize:   [50, 64], // size of the shadow
      iconAnchor:   [40, 40], // point of the icon which will correspond to marker's location
     // shadowAnchor: [4, 62],  // the same for the shadow
      popupAnchor:  [-21, -40] // point from which the popup should open relative to the iconAnchor
});
      const RIcon = L.icon({
        // tslint:disable-next-line:max-line-length
        // iconUrl: 'https://cdn0.iconfinder.com/data/icons/travel-vacation/289/travel-transport-hotel-vacation-holidays-tourist-tourism-travelling-traveling_164-512.png',
        iconUrl: 'https://raw.githubusercontent.com/ZeleDavid/ParkAnywhere/master/Documentation/Pin-slike/r.png',
        iconSize:     [40, 40], // size of the icon
        // shadowSize:   [50, 64], // size of the shadow
        iconAnchor:   [40, 40], // point of the icon which will correspond to marker's location
        // shadowAnchor: [4, 62],  // the same for the shadow
        popupAnchor:  [-21, -40] // point from which the popup should open relative to the iconAnchor
      });

      this.parkirneHise$.forEach(function (hisa) {
        hisa.forEach(function (podatki) {
          if ( podatki.stZasedenihMest === podatki.stVsehMest) {
          L.marker([podatki.lat, podatki.lng],  {icon: RIcon}).addTo(map1)
            .bindPopup(
              '<p><b>' + podatki.naziv + '</b></br>' +
              podatki.naslov + '<p>' +
              '<p>Število parkrnih mest:' + podatki.stVsehMest + ' </br>' +
              'Število zasedenih mest: ' + podatki.stZasedenihMest + '</p>'
            );
          } else {
            L.marker([podatki.lat, podatki.lng],  {icon: MIcon}).addTo(map1)
              .bindPopup(
                '<p><b>' + podatki.naziv + '</b></br>' +
                podatki.naslov + '<p>' +
                '<p><b>Lastnik: </b>' + podatki.lastnik + '</p>' +
                '<p>Število parkrnih mest:' + podatki.stVsehMest + ' </br>' +
                'Število zasedenih mest: ' + podatki.stZasedenihMest + '</p>'
              );
          }
        });
      });

    }
}
