import { Component, OnInit } from '@angular/core';
import { GenericService } from '../generic.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-document',
  templateUrl: './add-document.component.html',
  styleUrls: ['./add-document.component.css']
})
export class AddDocumentComponent implements OnInit {
  
    constructor(private service: GenericService, private router: Router) { }
  
    ngOnInit(): void {
    }

    addDocument(author: string, title: string, number_of_pages: string, type: string, format: string): void {
      this.service.addDocument(author, title, Number(number_of_pages), type, format).subscribe(_ => {
        this.router.navigate(['showDocuments']).then(_ => {
        });
      });
    }

    cancelAdd(): void {
      this.router.navigate(['showDocuments']).then(_ => {
      });
    }

}
