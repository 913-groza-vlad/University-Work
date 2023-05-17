import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DocumentsComponent } from './documents/documents.component';
import { DeleteDocumentComponent } from './delete-document/delete-document.component';
import { AddDocumentComponent } from './add-document/add-document.component';
import { UpdateDocumentComponent } from './update-document/update-document.component';

@NgModule({
  declarations: [
    AppComponent,
    DocumentsComponent,
    DeleteDocumentComponent,
    AddDocumentComponent,
    UpdateDocumentComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
