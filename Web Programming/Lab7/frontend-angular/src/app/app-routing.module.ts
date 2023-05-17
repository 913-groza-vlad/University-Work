import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DocumentsComponent } from './documents/documents.component';
import { DeleteDocumentComponent } from './delete-document/delete-document.component';
import { AddDocumentComponent } from './add-document/add-document.component';
import { UpdateDocumentComponent } from './update-document/update-document.component';

const routes: Routes = [
  {path: '', redirectTo: '/showDocuments', pathMatch: 'full'},
  {path: 'showDocuments', component: DocumentsComponent},
  {path: 'deleteDocument', component: DeleteDocumentComponent},
  {path: 'addDocument', component: AddDocumentComponent},
  {path: 'updateDocument', component: UpdateDocumentComponent}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
