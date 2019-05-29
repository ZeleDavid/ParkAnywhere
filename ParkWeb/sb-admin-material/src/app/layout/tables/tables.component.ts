import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import {Observable} from 'rxjs';
import {map, filter, switchMap, tap} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import * as _ from 'lodash';

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

@Component({
    selector: 'app-tables',
    templateUrl: './tables.component.html',
    styleUrls: ['./tables.component.scss']
})
export class TablesComponent implements OnInit {
    displayedColumns = ['naziv', 'naslov', 'cenaNaUro', 'stZasedenihMest', 'stVsehMest', 'zasedenost', 'lastnik'];
    dataSource: MatTableDataSource<ParkirnaHisa>;
    parkirneHise$: Observable<ParkirnaHisa[]>;

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    constructor(private http: HttpClient ) {

        this.parkirneHise$ = this.http
          .get<ParkirnaHisa[]>('http://localhost:8080/Docker_rest/REST/parkirneHise')
          .pipe(map(data => _.values(data)))
        ;


      let parkHise: ParkirnaHisa[] = new Array();
      this.http
        .get('http://localhost:8080/Docker_rest/REST/parkirneHise')
        .pipe(
          map(data => _.values(data)),
          tap(parkirneHise => this.dataSource = new MatTableDataSource(parkirneHise))    // users array [Object, Object, Object]
        )
        .subscribe(parkirneHise => parkHise = parkirneHise);

    }

    ngOnInit() {
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;

    }

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.dataSource.filter = filterValue;
        if (this.dataSource.paginator) {
            this.dataSource.paginator.firstPage();
        }
    }
}

/** Constants used to fill up our data base. */
const COLORS = [
    'maroon',
    'red',
    'orange',
    'yellow',
    'olive',
    'green',
    'purple',
    'fuchsia',
    'lime',
    'teal',
    'aqua',
    'blue',
    'navy',
    'black',
    'gray'
];
const NAMES = [
    'Maia',
    'Asher',
    'Olivia',
    'Atticus',
    'Amelia',
    'Jack',
    'Charlotte',
    'Theodore',
    'Isla',
    'Oliver',
    'Isabella',
    'Jasper',
    'Cora',
    'Levi',
    'Violet',
    'Arthur',
    'Mia',
    'Thomas',
    'Elizabeth'
];

export interface UserData {
    id: string;
    naziv: string;
    zasedenost: string;
    lastnik: string;
}

/** Builds and returns a new User. */
function createNewUser(id: number): UserData {
    const naziv =
        NAMES[Math.round(Math.random() * (NAMES.length - 1))] +
        ' ' +
        NAMES[Math.round(Math.random() * (NAMES.length - 1))].charAt(0) +
        '.';
    const lastnik = 'David';

    return {
        id: id.toString(),
        naziv: naziv,
        zasedenost: Math.round(Math.random() * 100).toString(),
        lastnik: lastnik
    };
}
