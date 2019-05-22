import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-pregled',
  templateUrl: './pregled.component.html',
  styleUrls: ['./pregled.component.scss']
})
export class PregledComponent implements OnInit {

 loadScripts() {
    const dynamicScripts = [
   'https://unpkg.com/leaflet@1.4.0/dist/leaflet.js'
    ];
    for (let i = 0; i < dynamicScripts.length; i++) {
      const node = document.createElement('script');
      node.src = dynamicScripts[i];
      node.type = 'text/javascript';
      node.async = false;
      node.charset = 'utf-8';
      document.getElementsByTagName('head')[0].appendChild(node);
    }
  }

  constructor() {
}

  ngOnInit() {
  }

}
