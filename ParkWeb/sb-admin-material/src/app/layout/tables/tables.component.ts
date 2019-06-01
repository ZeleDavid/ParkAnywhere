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
      let parkHise: ParkirnaHisa[] = new Array();
      this.http
        .get('http://localhost:8080/Docker_rest/REST/parkirneHise')
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
}

