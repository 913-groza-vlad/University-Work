import { Component, OnInit } from '@angular/core';
import { GenericService } from '../generic.service';
import { Router } from '@angular/router';
import { Document } from '../document';

@Component({
  selector: 'app-documents',
  templateUrl: './documents.component.html',
  styleUrls: ['./documents.component.css']
})
export class DocumentsComponent implements OnInit {

    documents: Document[] = [];

    constructor(private service: GenericService, private router: Router) { }
  
    ngOnInit(): void {
      console.log("ngOnInit called for DocumentsComponent");
      this.getDocuments('', '');
    }
    
    getDocuments(type: string, format: string): void {
      this.service.fetchDocuments(type, format).subscribe(documents => this.documents = documents);
    }

    toDeleteDocument(documentId: number): void {
      this.router.navigate(['deleteDocument'], {queryParams: {id: documentId}});
    }
  
    toAddDocument(): void {
      this.router.navigate(['addDocument']).then(_ => {
      });
    }
  
    toUpdateDocument(documentId: number): void {
      this.router.navigate(['updateDocument'], {queryParams: {id: documentId}}).then(_ => {
      });
    }

}
