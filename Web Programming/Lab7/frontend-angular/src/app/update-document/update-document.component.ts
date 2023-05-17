import { Component, OnInit } from '@angular/core';
import { GenericService } from '../generic.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-document',
  templateUrl: './update-document.component.html',
  styleUrls: ['./update-document.component.css']
})
export class UpdateDocumentComponent implements OnInit {
    document: any = {
      author: '',
      title: '',
      number_of_pages: 0,
      type: '',
      format: ''
    };
  
    constructor(private service: GenericService, private router: Router, private route: ActivatedRoute) { }

    ngOnInit(): void {
      const id = this.route.snapshot.queryParams['id'];
      this.service.getDocument(id).subscribe((data: any) => {
        this.document = data;
      });
    }


    updateDocument(author: string, title: string, number_of_pages: string, type: string, format: string): void {
      const id = this.route.snapshot.queryParams['id'];
      this.service.updateDocument(id, author, title, Number(number_of_pages), type, format).subscribe(() => {
        this.router.navigate(['showDocuments']).then(_ => {
        });
      });
   }

    cancelUpdate(): void {
      this.router.navigate(['showDocuments']).then(_ => {
      });
   }

}
