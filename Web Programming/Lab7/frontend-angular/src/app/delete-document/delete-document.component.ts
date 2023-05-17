import { Component, OnInit } from '@angular/core';
import { GenericService } from '../generic.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-delete-document',
  templateUrl: './delete-document.component.html',
  styleUrls: ['./delete-document.component.css']
})
export class DeleteDocumentComponent implements OnInit {
  documentId!: number;

  constructor(private service: GenericService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.documentId = +this.route.snapshot.queryParams['id'];
  }

  confirmDelete(): void {
    this.service.deleteDocument(this.route.snapshot.queryParams['id']).subscribe(() => {
      this.router.navigate(['showDocuments']).then(_ => {
      });
    });
  }

  cancelDelete(): void {
    this.router.navigate(['showDocuments']).then(_ => {
    });
  }
}
